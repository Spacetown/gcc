/* DatagramChannel.java -- 
   Copyright (C) 2002 Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package java.nio.channels;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * @since 1.4
 */
public abstract class DatagramChannel
  extends AbstractSelectableChannel
  implements ByteChannel, ScatteringByteChannel, GatheringByteChannel
{
  /**
   * Initializes the channel.
   */
  protected DatagramChannel (SelectorProvider provider)
  {
    super (provider);
  }

  /**
   * Opens a datagram channel.
   */
  public static DatagramChannel open () throws IOException
  {
    return SelectorProvider.provider ().openDatagramChannel ();
  }
  
  /**
   * Reads data from this channel.
   */
  public final long read (ByteBuffer[] dsts) throws IOException
  {
    long b = 0;
    
    for (int i = 0; i < dsts.length; i++)
      b += read (dsts[i]);
    
    return b;
  }
  
  /**
   * Writes data to this channel.
   */
  public final long write (ByteBuffer[] srcs)
  {
    long b = 0;
    
    for (int i = 0;i < srcs.length; i++)
      b += write (srcs[i]);
    
    return b;
  }
    
  /**
   * Connects this channel's socket.
   */
  public abstract DatagramChannel connect (SocketAddress remote);

  /**
   * Disonnects this channel's socket.
   */
  public abstract DatagramChannel disconnect ();

  /**
   * Tells whether or not this channel's socket is connected.
   */
  public abstract boolean isConnected ();
  
  /**
   * Reads data from this channel.
   */
  public abstract int read (ByteBuffer dst);
  
  /**
   * Reads data from this channel.
   */
  public abstract long read (ByteBuffer[] dsts, int offset, int length);
 
  /**
   * Receives a datagram via this channel.
   */
  public abstract SocketAddress receive (ByteBuffer dst);
 
  /**
   * Sends a datagram via this channel.
   */
  public abstract int send (ByteBuffer src, SocketAddress target);
 
  /**
   * Retrieves the channel's socket.
   */
  public abstract DatagramSocket socket ();
  
  /**
   * Writes data to this channel.
   */
  public abstract int write (ByteBuffer src);
  
  /**
   * Writes data to this channel.
   */
  public abstract long write (ByteBuffer[] srcs, int offset, int length);

  /**
   * Retrieves the valid operations for this channel.
   */
  public final int validOps ()
  {
    return SelectionKey.OP_READ | SelectionKey.OP_WRITE;
  }    
}
