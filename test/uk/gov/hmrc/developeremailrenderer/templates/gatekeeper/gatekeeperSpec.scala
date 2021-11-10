/*
 * Copyright 2021 HM Revenue & Customs
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
import org.scalatest.{ Matchers, WordSpec }
import uk.gov.hmrc.developeremailrenderer.templates.gatekeeper

class gatekeeperSpec extends WordSpec with Matchers {

  val applicationName = "Application Name"
  val role = "role"
  val article = "a"
  val developerHubTitle = "Developer Hub Title"

  val templateParams = Map(
    "applicationName" -> applicationName,
    "body"            -> "This is the body.",
    "subject"         -> "This is the subject.",
    "borderColour"    -> "#005EA5"
  )

  "htmlView" should {
    "render as" in new TestCase {
      val renderedHtml = gatekeeper.html.gatekeeper.render(templateParams)
      renderedHtml.contentType should include("text/html")
      renderedHtml.body should include(
        "<p style=\"margin: 0 0 30px; font-size: 19px;\">You are now " + article + " " + role +
          " on " + applicationName + ".</p>")
      renderedHtml.body should include("<p style=\"margin: 0 0 30px; font-size: 19px;\">From HMRC Developer Hub</p>")
    }
    "render with developerHubTitle" in new TestCase {
      val templateParamsPlus = templateParams + ("developerHubTitle" -> developerHubTitle)
      val renderedHtml = gatekeeper.html.gatekeeper.render(templateParamsPlus)
      renderedHtml.body should include(
        "<p style=\"margin: 0 0 30px; font-size: 19px;\">You are now " + article + " " + role +
          " on " + applicationName + ".</p>")
      renderedHtml.body should include(
        "<p style=\"margin: 0 0 30px; font-size: 19px;\">From HMRC " + developerHubTitle + "</p>")
    }
  }

  "textView" should {
    "render as" in new TestCase {
      val renderedTxt = gatekeeper.html.gatekeeper.render(templateParams)
      renderedTxt.contentType should include("text/plain")
      renderedTxt.body should include("You are now " + article + " " + role + " on " + applicationName + ".")
      renderedTxt.body should include("From HMRC Developer Hub")
    }
    "render with developerHubTitle" in new TestCase {
      val templateParamsPlus = templateParams + ("developerHubTitle" -> developerHubTitle)
      val renderedTxt = gatekeeper.html.gatekeeper.render(templateParamsPlus)
      renderedTxt.body should include("You are now " + article + " " + role + " on " + applicationName + ".")
      renderedTxt.body should include("From HMRC " + developerHubTitle)
    }
  }
}
