package user_service.config

@Target(AnnotationTarget.FUNCTION)
annotation class RunSql (val scripts : Array<String>)