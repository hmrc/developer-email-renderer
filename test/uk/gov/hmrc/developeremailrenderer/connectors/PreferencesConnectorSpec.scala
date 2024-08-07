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

package uk.gov.hmrc.developeremailrenderer.connectors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.mockito.ArgumentMatchers.{any, eq => eqTo}
import org.mockito.MockitoSugar
import org.scalatest.OptionValues
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

import uk.gov.hmrc.crypto.{ApplicationCrypto, PlainText}
import uk.gov.hmrc.http.client.{HttpClientV2, RequestBuilder}
import uk.gov.hmrc.http.{HeaderCarrier, StringContextOps}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig

import uk.gov.hmrc.developeremailrenderer.model.Language

class PreferencesConnectorSpec extends AnyWordSpecLike with Matchers with OptionValues with MockitoSugar with GuiceOneAppPerSuite with ScalaFutures {

  trait TestCase {
    implicit val headerCarrier: HeaderCarrier = new HeaderCarrier()
    val servicesConfig                        = mock[ServicesConfig]
    val httpClient                            = mock[HttpClientV2]
    val requestBuilderMock                    = mock[RequestBuilder]
    when(servicesConfig.baseUrl("preferences")).thenReturn("http://localhost")
    val crypto                                = app.injector.instanceOf[ApplicationCrypto]
    val preferencesConnector                  = new PreferencesConnector(servicesConfig, httpClient, crypto)
    val email                                 = "test@tetst.com"
    val encryptedEmail                        = new String(crypto.QueryParameterCrypto.encrypt(PlainText(email)).toBase64)
    val url                                   = servicesConfig.baseUrl("preferences") + s"/preferences/language/$encryptedEmail"
  }

  "PreferencesConnector language by email" should {
    "return English if preference returns English" in new TestCase {
      when(httpClient.get(eqTo(url"$url"))(any())).thenReturn(requestBuilderMock)
      when(requestBuilderMock.execute[Language](any(), any())).thenReturn(Future.successful(Language.ENGLISH))
      preferencesConnector.languageByEmail(email).futureValue shouldBe (Language.ENGLISH)
    }
  }

}
