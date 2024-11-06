import com.example.rmaproject2.data.datasource.local.CourseDataBase
import com.example.rmaproject2.data.datasource.remote.CourseService
import com.example.rmaproject2.data.repositories.CourseRepository
import com.example.rmaproject2.data.repositories.CourseRepositoryImpl
import com.example.rmaproject2.presentation.viewModels.CourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val courseModule = module {

    viewModel { CourseViewModel(courseRepository = get()) }

    single<CourseRepository> { CourseRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<CourseDataBase>().getCourseDao() }

    single<CourseService> { create(retrofit = get()) }

}