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

package uk.gov.hmrc.developeremailrenderer.templates.helpers

import java.util
import scala.language.reflectiveCalls

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import org.commonmark.Extension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

import play.twirl.api.Html

object Markdown {

  import org.commonmark.ext.gfm.tables.TablesExtension;

  val extensions = util.Arrays.asList(TablesExtension.create());
  val parser     = Parser
    .builder()
    .extensions(extensions)
    .build();
  val renderer   = HtmlRenderer
    .builder()
    .extensions(extensions)
    .build();

  def apply(text: String): Html = {
    val document = parser.parse(text)
    Html(
      Html(renderer.render(document))
        .toString()
        .replace("<table>", "<table style=\"border:1px solid black;border-collapse:collapse\">")
        .replace("<th>", "<th style=\"border:1px solid black;border-collapse:collapse\">")
        .replace("<td>", "<td style=\"border:1px solid black;border-collapse:collapse\">")
    )
  }

}
