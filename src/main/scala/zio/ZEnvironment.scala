package zio

import scala.reflect.{ClassTag}

trait ZEnvironment[+R] {

  def add[A](a: A)(implicit atag: ClassTag[A]): ZEnvironment[R with A]

  def get[A >: R](implicit atag: ClassTag[A]): A

}

object ZEnvironment {
  def apply() = new ZEnvironmentLive(Map.empty[ClassTag[_], Any])
}

// what is the actually type of the K part of Map[ClassTag[_], Any]?
// val int = implicitly[ClassTag[String]]
// val str = implicitly[ClassTag[Int]]
// val list = implicitly[ClassTag[List[Int]]]

// Set(int):            Set[scala.reflect.ClassTag[String]]
// Set(int, str):       Set[scala.reflect.ClassTag[_ >: Int with String]]
// Set(int, str, list): Set[scala.reflect.ClassTag[_ >: List[Int] with Int with String]] 
class ZEnvironmentLive[+R](typeMap: Map[ClassTag[_], Any]) extends ZEnvironment[R] {

  def add[A](a: A)(implicit atag: ClassTag[A]): ZEnvironment[R with A] = {
    new ZEnvironmentLive[R with A](typeMap ++ Map(atag -> a) )
  }

  def get[A >: R](implicit atag: ClassTag[A]): A = {
    typeMap(atag).asInstanceOf[A]
  }
}