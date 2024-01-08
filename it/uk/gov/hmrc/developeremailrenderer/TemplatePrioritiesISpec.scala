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

package uk.gov.hmrc.developeremailrenderer

import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{ServerProvider, WsScalaTestClient}

import uk.gov.hmrc.play.http.test.ResponseMatchers

class TemplatePrioritiesISpec
    extends AnyWordSpecLike
    with Matchers
    with OptionValues
    with WsScalaTestClient
    with GuiceOneServerPerSuite
    with ScalaFutures
    with ResponseMatchers
    with ServerProvider
    with TableDrivenPropertyChecks {

  object TestTemplates {

    val standard = Table[String, Map[String, String]](
      ("templateIds", "params"),
      ("gatekeeper", Map("recipientName_forename" -> "Ms Jane Doe", "subject" -> "test subject", "body" -> "test body"))
    )
  }

}
