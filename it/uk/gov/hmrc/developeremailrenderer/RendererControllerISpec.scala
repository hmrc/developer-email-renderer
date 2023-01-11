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
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{ServerProvider, WsScalaTestClient}
import play.api.libs.json._
import play.api.libs.ws.WSClient
import uk.gov.hmrc.auth.core.retrieve.v2.Retrievals.applicationName
import uk.gov.hmrc.play.http.test.ResponseMatchers

class RendererControllerISpec
    extends AnyWordSpecLike
    with Matchers
    with OptionValues
    with WsScalaTestClient
    with GuiceOneServerPerSuite
    with ScalaFutures
    with ResponseMatchers
    with ServerProvider {
  "POST /templates/:templateId" should {

    "return 200 and yield the rendered template data when supplied a valid templateId thats not defined in WelshTemplatesByLangPreference" in {
      val params                = Map(
        "applicationName"      -> "gatekeeper",
        "body"                 -> "This is the body.",
        "staticAssetUrlPrefix" -> "http://uri",
        "staticAssetVersion"   -> "v1",
        "subject"              -> "This is the subject.",
        "logoAssetUrlPrefix"   -> "http://localhost:9680/api-documentation/assets/images/",
        "borderColour"         -> "#005EA5",
        "showFooter"           -> "true",
        "showHmrcBanner"       -> "true",
        "firstName"            -> "m",
        "lastName"             -> "l"
      )
      implicit val ws: WSClient = app.injector.instanceOf(classOf[WSClient])

      val response = wsUrl(s"/templates/gatekeeper").post(Json.obj("parameters" -> params))
      response should have(
        status(200),
        jsonProperty(__ \ "fromAddress", "Software Developer Support Team <noreply@tax.service.gov.uk>"),
        jsonProperty(__ \ "subject", "This is the subject."),
        jsonProperty(__ \ "service", "gatekeeper"),
        jsonProperty(__ \ "plain"),
        jsonProperty(__ \ "html")
      )
    }

    "return 404 when a non-existent templateId is specified on the path" in {
      implicit lazy val wsc: WSClient = app.injector.instanceOf[WSClient]

      wsUrl(s"/templates/nonExistentTemplateId").post(Json.obj("parameters" -> Map.empty[String, String])) should have(status(404))
    }

    "return 400 and indicate the first point of failure when the parameters for the template are not supplied and its not in WelshTemplatesByLangPreference" in {
      implicit lazy val wsc: WSClient = app.injector.instanceOf[WSClient]

      wsUrl(s"/templates/gatekeeper")
        .post(Json.obj("parameters" -> Map.empty[String, String])) should have(
        status(400),
        jsonProperty(__ \ "reason", "key not found: body")
      )
    }
  }

}
