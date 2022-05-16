/*
 * Copyright 2022 HM Revenue & Customs
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

package preview

import java.util.UUID

object TemplateParams {
  val exampleLinkWithRandomId = s"http://host:8080/your/link/${UUID.randomUUID}"

  val newMessageAlert_Names = Map(
    "recipientName_title"          -> "Mr",
    "recipientName_forename"       -> "Rich",
    "recipientName_secondForename" -> "The Cat",
    "recipientName_surname"        -> "Johnston",
    "recipientName_honours"        -> "PA"
  )

  val testServiceUpdate = "[Service Name]"

  val exampleParams = Map(
    "gatekeeper" -> Map(
      "subject" -> "Sample subject",
      "body"    -> "Sample Body",
      "service" -> "gatekeeper",
      "showFooter" -> "true",
      "showHmrcBanner" -> "true",
      "firstName" -> "abc",
      "lastName" -> "def"
    )
  )
}
