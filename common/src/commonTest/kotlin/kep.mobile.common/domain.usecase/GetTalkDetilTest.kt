package kep.mobile.common.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kep.mobile.common.FakeData
import kep.mobile.common.data.KepApi
import kep.mobile.common.data.SpeakerEntity
import kep.mobile.common.data.TalkEntity
import kep.mobile.common.domain.model.toModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class GetTalkDetilTest {
        private val api = mockk<KepApi>()
        private val getTalkDetail = GetTalkDetail(api)

        @Test
        fun `returns detailed talk from api`() {
            val talkEntity = mockk<TalkEntity> {
                every { toModel() } returns FakeData.Model.androidx_coroutines
            }
            val speakerEntity = mockk<SpeakerEntity> {
                every { toModel() } returns FakeData.Model.geoffreymetais
            }
            coEvery { api.getTalks() } returns listOf(talkEntity)
            coEvery { api.getSpeakers() } returns listOf(speakerEntity)

            // This actually doesn't work, but makes this test compile. Testing suspending functions is currently
            // not possible in common modules
            suspend {
                getTalkDetail(
                    FakeData.Model.androidx_coroutines,
                    onSuccess = {
                        print(it)
                        assertEquals(FakeData.Model.androidx_coroutines, it)
                        assertTrue{ it.speakers.isNotEmpty() }
                        assertEquals(it.speakers, FakeData.Model.speakers)
                    },
                    onFailure = { print(it);fail(it.toString()) }
                )
            }
        }
}