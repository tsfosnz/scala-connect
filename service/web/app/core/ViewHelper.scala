package core

object ViewHelper {

  def paging(page: Int, count: Int, total: Int) = {

    val last = Math.ceil(total / count).toInt

    val pagination = page % 5 == 0 match {
      case true =>
        val start = Math.max(page, 1) >= last match {
          case true => Math.max(1, last - 5)
          case _ => Math.max(page, 1)
        }
        Range(start, Math.min(page + 5, last))
      case _ =>
        val start = Math.max((page / 5) * 5, 1) >= last match {
          case true => Math.max(1, last - 5)
          case _ => Math.max((page / 5) * 5, 1)
        }
        Range(start, Math.min(start + 5, last))
    }
    pagination
  }

}
