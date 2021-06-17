package me.stevew.a7minuteworkout

class Constants {
    // companion == static
    companion object{
        fun defaultExerciseList(): ArrayList<ExerciseModel> {
            var exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
            exerciseList.add(jumpingJacks)

            val abdominalCrunch = ExerciseModel(1, "Abdominal Crunches", R.drawable.ic_abdominal_crunch, false, false)
            exerciseList.add(abdominalCrunch)

            val highKnee = ExerciseModel(1, "High Knee Running In Place", R.drawable.ic_high_knees_running_in_place, false, false)
            exerciseList.add(highKnee)

            val lunge = ExerciseModel(1, "Lunges", R.drawable.ic_lunge, false, false)
            exerciseList.add(lunge)

            val plank = ExerciseModel(1, "Planks", R.drawable.ic_plank, false, false)
            exerciseList.add(plank)

            val pushUp = ExerciseModel(1, "Push Ups", R.drawable.ic_push_up, false, false)
            exerciseList.add(pushUp)

            val pushUpRotation = ExerciseModel(1, "Push Ups With Rotation", R.drawable.ic_push_up_and_rotation, false, false)
            exerciseList.add(pushUpRotation)

            val sidePlank = ExerciseModel(1, "Side Planks", R.drawable.ic_side_plank, false, false)
            exerciseList.add(sidePlank)

            val squat = ExerciseModel(1, "Squats", R.drawable.ic_squat, false, false)
            exerciseList.add(squat)

            val stepUps = ExerciseModel(1, "Step Up Onto Chair", R.drawable.ic_step_up_onto_chair, false, false)
            exerciseList.add(stepUps)

            val tricepDip = ExerciseModel(1, "Tricep Dips", R.drawable.ic_triceps_dip_on_chair, false, false)
            exerciseList.add(tricepDip)

            val wallSit = ExerciseModel(1, "Wall Sit", R.drawable.ic_wall_sit, false, false)
            exerciseList.add(wallSit)

            return exerciseList
        }
    }
}