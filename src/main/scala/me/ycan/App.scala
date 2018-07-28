package me.ycan

object App {

  def size(list: List[Int]): Int = {
    list match {
      case Nil => 0
      case _ :: t => 1  + size(t)
    }
  }

  def reverse(list: List[Int]): List[Int] = {
    list match {
      case Nil => Nil
      case h::t => reverse(t) ::: List(h)
    }
  }

}
