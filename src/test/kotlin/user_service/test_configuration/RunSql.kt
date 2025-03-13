package user_service.test_configuration

@Target(AnnotationTarget.FUNCTION)
annotation class RunSql (val scripts : Array<String>)