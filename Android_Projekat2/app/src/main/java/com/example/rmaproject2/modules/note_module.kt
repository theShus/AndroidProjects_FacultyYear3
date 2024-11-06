import com.example.rmaproject2.data.datasource.local.NoteDataBase
import com.example.rmaproject2.data.models.note.StatisticsHolder
import com.example.rmaproject2.data.repositories.NotesRepository
import com.example.rmaproject2.data.repositories.NotesRepositoryImpl
import com.example.rmaproject2.presentation.viewModels.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noteModule = module {

    viewModel { NotesViewModel(notesRepository = get(), StatisticsHolder(arrayOf(0,0,0,0,0), arrayOf(0,0,0,0,0)))}

    single<NotesRepository> { NotesRepositoryImpl(localDataSource = get()) }

    single { get<NoteDataBase>().getNoteDao() }

//    single<CourseService> { create(retrofit = get()) }

}