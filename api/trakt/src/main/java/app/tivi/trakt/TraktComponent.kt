/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.tivi.trakt

import app.tivi.inject.ApplicationScope
import com.uwetrottmann.trakt5.TraktV2
import com.uwetrottmann.trakt5.services.Episodes
import com.uwetrottmann.trakt5.services.Recommendations
import com.uwetrottmann.trakt5.services.Search
import com.uwetrottmann.trakt5.services.Seasons
import com.uwetrottmann.trakt5.services.Shows
import com.uwetrottmann.trakt5.services.Sync
import com.uwetrottmann.trakt5.services.Users
import me.tatarka.inject.annotations.Provides
import okhttp3.OkHttpClient

interface TraktComponent {
    @ApplicationScope
    @Provides
    fun provideTrakt(
        client: OkHttpClient,
        oauthInfo: TraktOAuthInfo,
    ): TraktV2 = object : TraktV2(
        oauthInfo.clientId,
        oauthInfo.clientSecret,
        oauthInfo.redirectUri,
    ) {
        override fun okHttpClient(): OkHttpClient = client.newBuilder()
            .apply { setOkHttpClientDefaults(this) }
            .build()
    }

    @Provides
    fun provideTraktUsersService(traktV2: TraktV2): Users = traktV2.users()

    @Provides
    fun provideTraktShowsService(traktV2: TraktV2): Shows = traktV2.shows()

    @Provides
    fun provideTraktEpisodesService(traktV2: TraktV2): Episodes = traktV2.episodes()

    @Provides
    fun provideTraktSeasonsService(traktV2: TraktV2): Seasons = traktV2.seasons()

    @Provides
    fun provideTraktSyncService(traktV2: TraktV2): Sync = traktV2.sync()

    @Provides
    fun provideTraktSearchService(traktV2: TraktV2): Search = traktV2.search()

    @Provides
    fun provideTraktRecommendationsService(traktV2: TraktV2): Recommendations = traktV2.recommendations()
}