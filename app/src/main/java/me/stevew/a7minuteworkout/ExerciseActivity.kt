package me.stevew.a7minuteworkout

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener { // OnListener for TextToSpeech
    // rest timer variables
    private var restTimer: CountDownTimer? = null // initialized in onCreate
    private var restProgress = 0

    // exercise timer variables
    private var exerciseTimer: CountDownTimer? = null // initialized in onCreate
    private var exerciseProgress = 0

    // exercise list variables
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    // text to speech variable
    private var tts: TextToSpeech? = null // initialized in onCreate

    // media
    private var player: MediaPlayer? = null // initialized and played in onFinish of exercise timer

    // RecyclerView Adapter
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // TOOLBAR BACK BUTTON
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // When clicking 'back' button on toolbar
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initializing Text To Speech
        tts = TextToSpeech(this, this)

        // what should happen at start of activity?
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
        setupExerciseStatusRecyclerView()

    }

    // Destroys to completely end process
    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    // REST TIMER
    private fun setRestProgressBar() {
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 5 - restProgress
                tvTimer.text = (5 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()  // starts the timer
    }

    // EXERCISE TIMER
    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 10 - exerciseProgress
                tvTimerExercise.text = (10 - exerciseProgress).toString()
            }

            override fun onFinish() {
                /*
                - Alternative way is to parse the URI -
                val soundURI = Uri.parse("android:resource://me.stevew.a7minuteworkout/" + R.raw.press_start) ->
                MediaPlayer.create(applicationContext, soundURI)
                */
                // Grabs media variable and creates with (context, res id)
                player = MediaPlayer.create(applicationContext, R.raw.press_start)
                // Makes sure not to loop media
                player!!.isLooping = false
                // Play
                player!!.start()

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    Toast.makeText(
                            this@ExerciseActivity, "Congrats you finished your exercises for the day!", Toast
                        .LENGTH_SHORT
                    ).show()
                }
            }
        }.start()  // starts the timer
    }

    // SETUP THE REST TIMER
    private fun setupRestView() {

        llRestView.visibility = View.VISIBLE
        llExerciseRestView.visibility = View.GONE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

    }

    // SETUP THE EXERCISE TIMER
    private fun setupExerciseView() {

        llRestView.visibility = View.GONE
        llExerciseRestView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        // Calling Text To Speech
        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    // TEXT TO SPEECH
    // init for TextToSpeech from [OnInitListener]
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US) // sets language of text to speech variable
            // -> Checks if language isn't supported or language isn't installed on device then logs a message
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!!")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // RecyclerView setup
    private fun setupExerciseStatusRecyclerView() {
        rvExerciseStatus.layoutManager = LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL, false
        )
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }
}