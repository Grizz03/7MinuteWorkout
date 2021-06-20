package me.stevew.a7minuteworkout

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener { // OnListener for TextToSpeech

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    // Text To Speech variable
    private var tts: TextToSpeech? = null
    
    // media
    private var player: MediaPlayer? = null

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // Back button on toolbar
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // When clicking 'back' button on toolbar
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initializing Text To Speech
        tts = TextToSpeech(this, this)
        
        // what should happen at start of app?
        exerciseList = Constants.defaultExerciseList()
        setupRestView()
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
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }


    private fun setRestProgressBar() {
        progressBar.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
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

        exerciseTimer = object : CountDownTimer(31000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 31 - exerciseProgress
                tvTimerExercise.text = (31 - exerciseProgress).toString()
            }

            override fun onFinish() {
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
    // init for TextToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported!!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!!")
        }
    }
    private fun speakOut(text:String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}