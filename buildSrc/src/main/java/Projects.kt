object Projects {

    object Core {

        const val core = ":core"

        const val data = "$core:data"
        const val domain = "$core:domain"
        const val feature = "$core:feature"

    }

    object Domain {

        private const val start = ":domain"

        const val users = "$start:users"

    }

}