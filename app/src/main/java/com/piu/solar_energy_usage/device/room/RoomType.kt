import java.io.Serializable

data class RoomType (
    val name: String,
    val image: Int,
    val icon: Int
) : Serializable {

    override fun toString(): String {
        return name
    }
}