package me.stevew.a7minuteworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        // When clicking 'back' button on toolbar
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        setupRestView()
    }

    // When timer ends
    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        super.onDestroy()
    }


    private fun setRestProgressBar() {
        progressBar.progress = restProgress

        // Create restTimer object which is CountDownTimer(total time, interval)
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            // onFinish() method inside Timer object
            override fun onFinish() {
                setupExerciseView()
            }
        }.start()  // starts the timer
    }

    // EXERCISE TIMER
    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress

        // Create restTimer object which is CountDownTimer(total time, interval)
        exerciseTimer = object : CountDownTimer(46000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // increment timer
                exerciseProgress++
                // get the progress and do total time - (the incrementing value of exerciseProgress)
                progressBarExercise.progress = 46 - exerciseProgress
                // convert to string
                tvTimerExercise.text = (46 - exerciseProgress).toString()
            }

            // onFinish() method inside Timer object
            override fun onFinish() {
                // Simple toast after timer ends
                Toast.makeText(
                    this@ExerciseActivity, "Your exercise has ended.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()  // starts the timer
    }


    private fun setupRestView() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView() {

        llRestView.visibility = View.GONE
        llExerciseRestView.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

}