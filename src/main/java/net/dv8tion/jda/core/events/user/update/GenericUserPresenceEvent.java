/*
 *     Copyright 2015-2018 Austin Keener & Michael Ritter & Florian Spieß
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

package net.dv8tion.jda.core.events.user.update;

import net.dv8tion.jda.client.entities.Friend;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

/**
 * Indicates that the presence of a {@link net.dv8tion.jda.core.entities.User User} has changed.
 * <br>Users don't have presences directly, this is fired when either a {@link net.dv8tion.jda.core.entities.Member Member} from a {@link net.dv8tion.jda.core.entities.Guild Guild}
 * or one of the client's {@link net.dv8tion.jda.client.entities.Friend Friends} changes their presence.
 *
 * <p>Can be used to track the presence updates of members/friends.
 */
public interface GenericUserPresenceEvent
{
    /**
     * Possibly-null guild in which the presence has changed.
     *
     * @return The guild, or null if this is related to a {@link net.dv8tion.jda.client.entities.Friend Friend}
     */
    Guild getGuild();

    /**
     * Possibly-null member who changed their presence.
     *
     * @return The member, or null if this is related to a {@link net.dv8tion.jda.client.entities.Friend Friend}
     */
    Member getMember();

    /**
     * Possibly-null friend who changed their presence.
     *
     * @return The friend, or null if this is related to a {@link net.dv8tion.jda.core.entities.Member Member}
     */
    Friend getFriend();

    /**
     * Whether this is a change for a friend presence.
     *
     * @return True, if this was the presence update for a {@link net.dv8tion.jda.client.entities.Friend Friend}
     */
    default boolean isRelationshipUpdate()
    {
        return getGuild() == null;
    }
}
