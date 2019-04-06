package solutions

import cats._
import cats.data._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

object HigherKindedTypes extends App {

  //  trait HKT[F[_]]

  def product[A, B](listA: List[A], listB: List[B]): List[(A, B)] =
    for {
      a <- listA
      b <- listB
    } yield (a, b)

  //
  //  def product[A, B](optA: Option[A], optB: Option[B]): Option[(A, B)] = {
  //    for {
  //      a <- optA
  //      b <- optB
  //    } yield (a, b)
  //  }
  //
  //  def product[A, B](futA: Future[A], futB: Future[B]): Future[(A, B)] = {
  //    for {
  //      a <- futA
  //      b <- futB
  //    } yield (a, b)
  //  }

  //  trait FlatMapAdapter[F[_], A] {
  //    def flatMap[B](f: A => F[B]): F[B]
  //
  //    def map[B](f: A => B): F[B]
  //  }
  //
  //  implicit class FlatMapAdapterList[A](list: List[A]) extends FlatMapAdapter[List, A] {
  //    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)
  //
  //    override def map[B](f: A => B): List[B] = list.map(f)
  //  }
  //
  //  implicit class FlatMapAdapterOption[A](opt: Option[A]) extends FlatMapAdapter[Option, A] {
  //    override def flatMap[B](f: A => Option[B]): Option[B] = opt.flatMap(f)
  //
  //    override def map[B](f: A => B): Option[B] = opt.map(f)
  //  }
  //
  //  implicit class FlatMapAdapterFuture[A](fut: Future[A]) extends FlatMapAdapter[Future, A] {
  //    override def flatMap[B](f: A => Future[B]): Future[B] = fut.flatMap(f)
  //
  //    override def map[B](f: A => B): Future[B] = fut.map(f)
  //  }

  //  def product[F[_], A, B](ma: FlatMapAdapter[F, A], mb: FlatMapAdapter[F, B]): F[(A, B)] =
  //    for {
  //      a <- ma
  //      b <- mb
  //    } yield (a, b)
  //
  //  val res = product(new FlatMapAdapterList[Int](List(1, 2, 3)), new FlatMapAdapterList[String](List("A", "B")))
  //  val res2 = product(new FlatMapAdapterOption[Int](Some(3)), new FlatMapAdapterOption[String](Some("B")))
  //  val res3 = product(new FlatMapAdapterFuture[Int](Future.successful(1)), new FlatMapAdapterFuture[String](Future.successful("B")))
  //
  //  def product[F[_], A, B](implicit ma: FlatMapAdapter[F, A], mb: FlatMapAdapter[F, B]): F[(A, B)] =
  //    for {
  //      a <- ma
  //      b <- mb
  //    } yield (a, b)
  //
  //  val res = product(List(1, 2, 3), List("A", "B"))
  //  val res2 = product(Some(3), Some("B"))
  //  val res3 = product(Future.successful(1), Future.successful("B"))

  def product[F[_]: Monad, A, B](ma: F[A], mb: F[B]): F[(A, B)] =
    for {
      a <- ma
      b <- mb
    } yield (a, b)

  val three   = Future(3)
  val goodbye = Future("goodbye")

  val res  = product(List(1, 2, 3), List("a", "b"))
  val res2 = product(Option(3), Option("hello"))
  val res3 = product(three, goodbye)
  val res4 = product(Try(3), Try(4))
  val res5 = product(EitherT.right(three), EitherT.right(goodbye))

  println(res)
  println(res2)
  println(res3)
  println(res4)
  println(Await.ready(res5.value, Duration.Inf))
}

//import cats._
//import cats.implicits._
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.Future
//
//object HigherKindedTypes extends App {
//
//  trait HKT[F[_]]
//
//  // val test: List = List(1)
//
//  // cartesian product
//  def product[A, B](as: List[A], bs: List[B]): List[(A, B)] =
//    for {
//      a <- as
//      b <- bs
//    } yield (a, b)
//
//  def product[A, B](as: Option[A], bs: Option[B]): Option[(A, B)] =
//    for {
//      a <- as
//      b <- bs
//    } yield (a, b)
//
//  def product[A, B](as: Either[Any, A], bs: Either[Any, B]): Either[Any, (A, B)] =
//    for {
//      a <- as
//      b <- bs
//    } yield (a, b)
//
//  //  trait FlatMapAdapter[F[_], A] {
//  //    def map[B](f: A => B): F[B]
//  //    def flatMap[B](f: A => F[B]): F[B]
//  //  }
//  //
//  //  implicit class FlatMapAdapterList[A](ma: List[A]) extends FlatMapAdapter[List, A] {
//  //    override def map[B](f: A => B): List[B] = ma map f
//  //
//  //    override def flatMap[B](f: A => List[B]): List[B] = ma flatMap f
//  //  }
//  //
//  //  implicit class FlatMapAdapterOption[A](ma: Option[A]) extends FlatMapAdapter[Option, A] {
//  //    override def map[B](f: A => B): Option[B] = ma map f
//  //
//  //    override def flatMap[B](f: A => Option[B]): Option[B] = ma flatMap f
//  //  }
//  //
//  //  type EitherOps[A] = Either[Any, A]
//  //  implicit class FlatMapAdapterEither[A](ma: EitherOps[A]) extends FlatMapAdapter[EitherOps, A] {
//  //    override def map[B](f: A => B): EitherOps[B] = ma map f
//  //
//  //    override def flatMap[B](f: A => EitherOps[B]): EitherOps[B] = ma flatMap f
//  //  }
//  //
//  //  def product[F[_], A, B](implicit ma: FlatMapAdapter[F, A], mb: FlatMapAdapter[F, B]): F[(A, B)] =
//  //    for {
//  //      a <- ma
//  //      b <- mb
//  //    } yield (a, b)
//
//  //  def product[F[_]: Monad, A, B](ma: F[A], mb: F[B]): F[(A, B)] =
//  //    for {
//  //      a <- ma
//  //      b <- mb
//  //    } yield (a, b)
//
//  println(product(List(1, 2, 3), List("a", "b", "c")))
//  println(product(Option(1), Option("a")))
//
//  val right0: Either[Any, Int]    = Right(1)
//  val right1: Either[Any, String] = Right("a")
//  println(product(right0, right1))
