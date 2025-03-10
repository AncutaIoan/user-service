package config

@Target(AnnotationTarget.FUNCTION)
annotation class RunSql (val scripts : Array<String>)