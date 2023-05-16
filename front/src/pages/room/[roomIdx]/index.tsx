'use client';
import React, { useState, useEffect } from 'react';
import Ready from '@/components/room/ready/Ready';
import InGameRoom from '@/components/room/game/InGameRoom';
import GameResult from '@/components/room/result/GameResult';
import { useAppDispatch } from '@/store/thunkhook';
import { setRoomIdx } from '@/store/slice/game/gameRoom';
import { getCookie } from 'cookies-next';
import axios from 'axios';
import { memberAPI } from '@/api/api';
import type { GetServerSideProps } from 'next';
import wrapper from '@/store';
import { useAppSelector } from '@/store/thunkhook';
import { setLogin } from '@/store/slice/loginSlice';
import { setMypage } from '@/store/slice/mypageSlice';
import { useRouter } from 'next/router';

export default function Room({
  roomIdx,
  message,
  mydata,
}: {
  mydata: string;
  message: string;
  roomIdx: string;
}) {
  const router = useRouter();
  const [userId, setUserId] = useState<string>('');
  const [room, setRoom] = useState<string>('');
  const [status, setStatus] = useState<string>('ready');
  const [socket, setSocket] = useState<WebSocket | null>(null);
  console.log(`mydata -> ${mydata}`);

  const dispatch = useAppDispatch();
  useEffect(() => {
    if (message === 'notLogin') {
      console.log('로그인해라');
      router.push('/');
    }
  });

  useEffect(() => {
    if (!socket) {
      dispatch(setRoomIdx({ roomIdx }));
    }
  }, [dispatch, socket, roomIdx]);

  switch (status) {
    case 'ready':
      return (
        <Ready
          userId={userId}
          setUserId={setUserId}
          room={room}
          setRoom={setRoom}
          setStatus={setStatus}
          socket={socket}
          setSocket={setSocket}
        />
      );
    case 'gameStart':
      return <InGameRoom game="CatchMind" socket={socket as WebSocket} />;
    case 'gameEnd':
      return <GameResult socket={socket as WebSocket} />;
    default:
      return <div>Something wrong!!!</div>;
  }
}

export const getServerSideProps: GetServerSideProps =
  wrapper.getServerSideProps((store) => async (context: any) => {
    const { req, res } = context;
    let refreshtoken = getCookie('refreshtoken', { req, res });
    let accesstoken = getCookie('accesstoken', { req, res });
    const api = axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_URL,
      headers: {
        Authorization: accesstoken,
        'Content-Type': 'application/json',
        Cookie: `refreshtoken=` + refreshtoken,
      },
    });
    try {
      const re = await api.get(`/user/mypage/profile`);
      const res = re.data.body;
      store.dispatch(setLogin({ isLogin: true }));
      store.dispatch(setMypage(res));
      return {
        props: {
          message: 'Login',
          mydata: 'res',
          roomIdx: context.params?.roomIdx || 'noRoom',
        },
      };
    } catch (e) {
      console.log(e);
      return {
        props: {
          message: 'notLogin',
          mydata: '',
          roomIdx: '',
        },
      };
    } finally {
      api.defaults.headers.Cookie = '';
    }
  });
