package zio

import scala.reflect._
import org.scalatest.funsuite.AnyFunSuite

class ZEnvironmentSpec extends AnyFunSuite {

  test("set 90 and get Int") {
    val env = ZEnvironment().add(90)
    assert(90 == env.get[Int])
  }

  test("set 'abc' and get String") {
    val env = ZEnvironment().add("abc")
    assert("abc" == env.get[String])
  }

  test("set List(1,2,3) and get List[Int]") {
    val env = ZEnvironment().add(List(1,2,3))
    assert(List(1,2,3) == env.get[List[Int]])
  }
}