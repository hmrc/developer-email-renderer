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

package uk.gov.hmrc.developeremailrenderer.templates


import uk.gov.hmrc.developeremailrenderer.templates.api.ApiTemplates
import uk.gov.hmrc.developeremailrenderer.templates.apicatalogue.ApiCatalogueTemplates


trait TemplateLocator {
  def templateGroups: Map[String, Seq[MessageTemplate]] =
    Map(
      "API Platform"          -> ApiTemplates.templates,
      "API Catalogue"         -> ApiCatalogueTemplates.templates
    )

  lazy val all: Seq[MessageTemplate] = templateGroups.values.flatten.toSeq

  def findTemplate(templateId: String): Option[MessageTemplate] =
    all.find(_.templateId == templateId) orElse {
      all.find(_.templateId == templateId.stripSuffix(TemplateLocator.WELSH_SUFFIX))
    }
}

object TemplateLocator extends TemplateLocator {
  final val WELSH_SUFFIX = "_cy"
}
