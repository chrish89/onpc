/*
 * Enhanced Music Controller
 * Copyright (C) 2018-2022 by Mikhail Kulesh
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. You should have received a copy of the GNU General
 * Public License along with this program.
 */

package com.mkulesh.onpc.iscp.messages;

import com.jayway.jsonpath.JsonPath;
import com.mkulesh.onpc.iscp.EISCPMessage;
import com.mkulesh.onpc.iscp.ISCPMessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
 * NET/USB Artist Name (variable-length, 64 ASCII letters max)
 */
public class ArtistNameMsg extends ISCPMessage
{
    public final static String CODE = "NAT";

    ArtistNameMsg(EISCPMessage raw) throws Exception
    {
        super(raw);
    }

    ArtistNameMsg(final String name)
    {
        super(0, name);
    }

    @NonNull
    @Override
    public String toString()
    {
        return CODE + "[" + data + "]";
    }

    /*
     * Denon control protocol
     */
    private final static String HEOS_COMMAND = "player/get_now_playing_media";

    @Nullable
    public static ArtistNameMsg processHeosMessage(@NonNull final String command, @NonNull final String heosMsg)
    {
        if (HEOS_COMMAND.equals(command))
        {
            final String name = JsonPath.read(heosMsg, "$.payload.artist");
            return new ArtistNameMsg(name);
        }
        return null;
    }
}
