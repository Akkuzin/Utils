job("Build") {
    host("Run build") {
        container(displayName = "Run mvn install", image = "maven:latest") {
            shellScript {
                content = """
    	            mvn clean verify
                """
            }
        }

        requirements {
            workerTags("home")

        }
    }
}