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

package uk.gov.hmrc.developeremailrenderer.templates.helpers

import akka.http.scaladsl.model.{ StatusCode, StatusCodes }

import scala.language.reflectiveCalls
import play.twirl.api.Html

object Markdown {

  def apply(text: String): Html = Html(process(text))

  import com.github.rjeschke.txtmark.{ Configuration, Processor }
  import org.markdown4j._

  private val emptyHtml = Html("")

  private def configuration =
    Configuration.builder.forceExtentedProfile
      .registerPlugins(new YumlPlugin, new WebSequencePlugin, new IncludePlugin)
      .setDecorator(
        new ExtDecorator()
          .addStyleClass("list list-bullet", "ul")
          .addStyleClass("list list-number", "ol")
          .addStyleClass("code--slim", "code")
          .addStyleClass("heading-large", "h1")
          .addStyleClass("heading-medium", "h2")
          .addStyleClass("heading-small", "h3")
          .addStyleClass("heading-small", "h4"))
      .setCodeBlockEmitter(new CodeBlockEmitter)

  private def process(text: String) = Processor.process(text, configuration.build)
}

