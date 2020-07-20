/*
 * Copyright 2015-2020 Austin Keener, Michael Ritter, Florian Spieß, and the JDA contributors
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

package net.dv8tion.jda.api.audio.hooks;

import net.dv8tion.jda.api.audio.SpeakingMode;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.EnumSet;

public class ListenerProxy implements ConnectionListener
{
    private static final Logger log = LoggerFactory.getLogger(ListenerProxy.class);
    private volatile ConnectionListener subject = null;

    @Override
    @SuppressWarnings("java:S1181") // Throwable and Error should not be caught
    public void onPing(long ping)
    {
        if (subject == null)
            return;
        ConnectionListener listener = this.subject;
        try
        {
            if (listener != null)
                listener.onPing(ping);
        }
        catch (Throwable t)
        {
            log.error("The ConnectionListener encountered and uncaught exception", t);
            if (t instanceof Error)
                throw (Error) t;
        }
    }

    @Override
    @SuppressWarnings("java:S1181") // Throwable and Error should not be caught
    public void onStatusChange(@Nonnull ConnectionStatus status)
    {
        if (subject == null)
            return;
        ConnectionListener listener = this.subject;
        try
        {
            if (listener != null)
                listener.onStatusChange(status);
        }
        catch (Throwable t)
        {
            log.error("The ConnectionListener encountered and uncaught exception", t);
            if (t instanceof Error)
                throw (Error) t;
        }
    }

    @Override
    @SuppressWarnings("java:S1181") // Throwable and Error should not be caught
    public void onUserSpeaking(@Nonnull User user, @Nonnull EnumSet<SpeakingMode> modes)
    {
        if (subject == null)
            return;
        ConnectionListener listener = this.subject;
        try
        {
            if (listener != null)
            {
                listener.onUserSpeaking(user, modes);
                listener.onUserSpeaking(user, modes.contains(SpeakingMode.VOICE));
                listener.onUserSpeaking(user, modes.contains(SpeakingMode.VOICE), modes.contains(SpeakingMode.SOUNDSHARE));
            }
        }
        catch (Throwable t)
        {
            log.error("The ConnectionListener encountered and uncaught exception", t);
            if (t instanceof Error)
                throw (Error) t;
        }
    }

    @Override
    public void onUserSpeaking(@Nonnull User user, boolean speaking) {}

    public void setListener(ConnectionListener listener)
    {
        this.subject = listener;
    }

    public ConnectionListener getListener()
    {
        return subject;
    }
}
