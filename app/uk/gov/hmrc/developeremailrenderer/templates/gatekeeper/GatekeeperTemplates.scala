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

package uk.gov.hmrc.developeremailrenderer.templates.gatekeeper

import uk.gov.hmrc.developeremailrenderer.domain.{ MessagePriority, MessageTemplate }
import uk.gov.hmrc.developeremailrenderer.templates.ServiceIdentifier.GateKeeper
import uk.gov.hmrc.developeremailrenderer.templates.FromAddress

/**
  * Templates used by the API Catalogue.
  */
object GatekeeperTemplates {

  private def extractFromAddress(params: Map[String, String]): String =
    FromAddress.noReply("Software Developer Support Team")

  val templates = Seq(
    MessageTemplate.createWithDynamicSubjectAndFromAddress(
      templateId = "gatekeeper",
      fromAddress = extractFromAddress,
      service = GateKeeper,
      subject = _.apply("subject"),
      plainTemplate = txt.gatekeeper.f,
      htmlTemplate = html.gatekeeper.f,
      priority = Some(MessagePriority.Standard)
    )
  )
}
