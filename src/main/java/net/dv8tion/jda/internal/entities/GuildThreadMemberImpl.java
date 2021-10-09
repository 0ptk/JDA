/*
 * Copyright 2015 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.internal.entities;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.utils.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.OffsetDateTime;

public class GuildThreadMemberImpl implements GuildThreadMember
{
    private final JDA api;
    private final GuildThreadImpl thread;
    
    private User user;
    private long joinedTimestamp;
    private long flags;

    public GuildThreadMemberImpl(User user, GuildThreadImpl thread)
    {
        this.api = user.getJDA();
        this.user = user;
        this.thread = thread;
    }

    @Nonnull
    @Override
    public JDA getJDA()
    {
        return api;
    }
    
    @Nonnull
    @Override
    public Guild getGuild()
    {
        //TODO is this actually how we want to get the guild? could store it locally in the entity instead if wanted
        return thread.getGuild();
    }
    
    @Nonnull
    @Override
    public GuildThread getThread()
    {
        return this.thread;
    }

    @Nonnull
    @Override
    public User getUser()
    {
        // Load user from cache if one exists, ideally two members with the same id should wrap the same user object
        User realUser = getJDA().getUserById(user.getIdLong());
        if (realUser != null)
            this.user = realUser;
        return user;
    }

    @Nullable
    @Override
    public Member getMember()
    {
        return getGuild().getMember(getUser());
    }

    @Nonnull
    @Override
    public OffsetDateTime getTimeJoined()
    {
        return Helpers.toOffset(joinedTimestamp);
    }

    @Override
    public long getFlagsRaw()
    {
        return flags;
    }

    @Nonnull
    @Override
    public String getAsMention()
    {
        //TODO: should this try to use the member instead of nickname resolution? Member could be null though...
        return user.getAsMention();
    }

    @Override
    public long getIdLong()
    {
        return user.getIdLong();
    }

    // ===== Setters =======

    public GuildThreadMemberImpl setJoinedTimestamp(long joinedTimestamp)
    {
        this.joinedTimestamp = joinedTimestamp;
        return this;
    }

    public GuildThreadMemberImpl setFlags(long flags)
    {
        this.flags = flags;
        return this;
    }
}
