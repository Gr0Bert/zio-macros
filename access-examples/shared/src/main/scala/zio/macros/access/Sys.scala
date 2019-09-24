/*
 * Copyright 2017-2019 John A. De Goes and the ZIO Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zio.macros.access

import zio.{URIO, ZIO}

@Accessable
trait Sys {

  val sys: Sys.Service[Any]
}

object Sys {

  val preValue: Int = 42

  trait Service[R] {

    def get(id: Int): ZIO[R, Nothing, String]
    def set(id: Int, value: String): ZIO[R, Nothing, Unit]
    def getAndSet(id: Int, value: String): ZIO[R, Nothing, String]
    def getAndSet2(id: Int)(value: String): ZIO[R, Nothing, String]
    def clear(): ZIO[R, Nothing, Unit]
    def clear2: ZIO[R, Nothing, Unit]
    val clear3: ZIO[R, Nothing, Unit]
  }

  val postValue: Int = 42
}

object ValidateAccessable {
  // if macro expands correctly code below should compile
  def get(id: Int): URIO[Sys, String]                       = Sys.>.get(id)
  def set(id: Int, value: String): URIO[Sys, Unit]          = Sys.>.set(id, value)
  def getAndSet(id: Int, value: String): URIO[Sys, String]  = Sys.>.getAndSet(id, value)
  def getAndSet2(id: Int)(value: String): URIO[Sys, String] = Sys.>.getAndSet2(id)(value)
  def clear(): URIO[Sys, Unit]                              = Sys.>.clear()
  def clear2: URIO[Sys, Unit]                               = Sys.>.clear2
  val clear3: URIO[Sys, Unit]                               = Sys.>.clear3

  val preValue: Int  = Sys.preValue
  val postValue: Int = Sys.postValue
}