import com.example.composenoteappdemo.feature_note.data.repository.FakeNoteRepository
import com.example.composenoteappdemo.feature_note.domain.model.InvalidNoteException
import com.example.composenoteappdemo.feature_note.domain.model.Note
import com.example.composenoteappdemo.feature_note.domain.usecase.AddNoteUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AddNoteUseCaseTest {

    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeNoteRepository = FakeNoteRepository()
        addNoteUseCase = AddNoteUseCase(fakeNoteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Add note with blank title, have exception`() = runBlockingTest {
        val note = Note(
            title = "",
            content = "a",
            timestamp = System.currentTimeMillis(),
            color = 0
        )

        assertThrowsSuspending<InvalidNoteException> {
            fakeNoteRepository.addNote(note)
        }
    }

    inline fun <reified T : Throwable> assertThrowsSuspending(
        noinline executable: suspend () -> Unit
    ): T = assertThrows(T::class.java) {
        runBlocking {
            executable()
        }
    }

}