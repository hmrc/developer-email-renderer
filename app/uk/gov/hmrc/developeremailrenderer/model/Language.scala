/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.developeremailrenderer.model

import scala.collection.immutable.ListSet

import play.api.libs.json._

sealed trait Language {

  lazy val displayText: String = {
    this.toString().toLowerCase().capitalize
  }
}

case object Language {

  case object ENGLISH extends Language

  val values: ListSet[Language] = ListSet[Language](ENGLISH)

  def apply(text: String): Option[Language] = Language.values.find(_.toString() == text.toUpperCase)

  def entryName(lang: Language): String = {
    lang match {
      case Language.ENGLISH => "en"
    }
  }

  implicit val languageReads: Reads[Language] = Reads[Language] { case _ =>
    JsSuccess(Language.ENGLISH)
  }

  implicit val languageWrites: Writes[Language] = new Writes[Language] {
    override def writes(e: Language): JsValue = JsString(Language.entryName(e))
  }
}
