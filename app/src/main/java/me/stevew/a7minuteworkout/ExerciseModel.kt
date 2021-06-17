package me.stevew.a7minuteworkout

class ExerciseModel(
    private var id: Int, private var name: String, private var image: Int, private var isCompleted:
    Boolean, private var isSelected: Boolean
) {
    // id
    fun getId(): Int {
        return id
    }

    fun setId(id: Int){
        this.id = id
    }

    // name
    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    // image
    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int){
        this.image = image
    }

    // isCompleted
    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isSelected = isCompleted
    }

    //isSelected
    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

}