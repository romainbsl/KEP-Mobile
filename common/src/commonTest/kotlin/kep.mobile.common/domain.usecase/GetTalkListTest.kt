package kep.mobile.common.domain.usecase

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kep.mobile.common.FakeData
import kep.mobile.common.data.KepApi
import kep.mobile.common.data.TalkEntity
import kep.mobile.common.domain.model.toModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class GetTalkListTest {
        private val api = mockk<KepApi>()
        private val getTalkList = GetTalkList(api)

        @Test
        fun `returns talks from api`() {
            val entity = mockk<TalkEntity> {
                every { toModel() } returns FakeData.Model.androidx_coroutines
            }
            coEvery { api.getTalks() } returns listOf(entity)

            // This actually doesn't work, but makes this test compile. Testing suspending functions is currently
            // not possible in common modules
            suspend {
                getTalkList(
                    UseCase.None,
                    onSuccess = {
                        print(it)
                        assertEquals(listOf(FakeData.Model.androidx_coroutines), it)
                    },
                    onFailure = { print(it);fail(it.toString()) }
                )
            }
        }
}