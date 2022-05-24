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

package uk.gov.hmrc.developeremailrenderer.templates.gatekeeper

import junit.framework.TestCase
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import uk.gov.hmrc.developeremailrenderer.templates.gatekeeper

class gatekeeperSpec extends AnyWordSpec with Matchers {

  val applicationName = "Application Name"
  val role = "role"
  val article = "a"
  val developerHubTitle = "Developer Hub Title"

  val templateParams = Map(
    "applicationName"      -> applicationName,
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

  "htmlView" should {
    "render as" in new TestCase {
      val renderedHtml = gatekeeper.html.gatekeeper.render(templateParams)
      renderedHtml.contentType should include("text/html")
      println(renderedHtml.body)
      renderedHtml.body should include("Manage the emails you receive from us in your")
    }
  }

  "textView" should {
    "render as" in new TestCase {
      val renderedTxt = gatekeeper.txt.gatekeeper.render(templateParams)
      renderedTxt.contentType should include("text/plain")
      renderedTxt.body should include("This is the body")
    }
  }

  val templateParams2 = Map(
    "applicationName"      -> applicationName,
    "body"                 -> "This is the body.",
    "staticAssetUrlPrefix" -> "http://uri",
    "staticAssetVersion"   -> "v1",
    "subject"              -> "This is the subject.",
    "logoAssetUrlPrefix"   -> "http://localhost:9680/api-documentation/assets/images/",
    "borderColour"         -> "#005EA5",
    "showFooter"           -> "false",
    "showHmrcBanner"       -> "false",
    "firstName"            -> "m",
    "lastName"             -> "l"
  )

  "htmlView for showFooter and showHmrcBanner" should {
    "render as " in new TestCase {
      val renderedHtml = gatekeeper.html.gatekeeper.render(templateParams2)
      renderedHtml.contentType should include("text/html")
      renderedHtml.body should include("<p><br>This is the body.<br></p>")
    }
  }

  "textView for showFooter and showHmrcBanner" should {
    "render as " in new TestCase {
      val renderedTxt = gatekeeper.txt.gatekeeper.render(templateParams2)
      renderedTxt.contentType should include("text/plain")
      renderedTxt.body should include("This is the body")
    }
  }
}
