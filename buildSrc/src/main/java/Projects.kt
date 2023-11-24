object Projects {

    object Core {

        const val core = ":core"

        const val data = "$core:data"
        const val domain = "$core:domain"
        const val feature = "$core:feature"

    }

    object Domain {

        private const val start = ":domain"

        const val users = "$start:search-users"

    }

    object Data {

        private const val start = ":data"

        const val users = "$start:search-users"

    }

    object Feature {

        private const val start = ":feature"

        const val search_users = "$start:search-users"
        const val user_profile = "$start:user-profile"

    }

}