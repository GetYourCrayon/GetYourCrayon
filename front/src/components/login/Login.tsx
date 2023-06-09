import { useState, forwardRef } from 'react';
import tw from 'tailwind-styled-components';
import { Button } from '../ui/Button';
import Image from 'next/image';

//mui 관련 import
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Slide from '@mui/material/Slide';

export interface LoginPropsType {
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const Transition = forwardRef(function Transition(
  props: { children: React.ReactElement },
  ref,
) {
  return <Slide direction="up" ref={ref} {...props} />;
});

const CustomDialogTitle = (props: any) => {
  const { children, onClose, ...other } = props;

  return (
    <DialogTitle sx={{ m: 0, p: 4 }} {...other}>
      {children}
      {onClose ? (
        <IconButton
          aria-label="close"
          onClick={() => {
            onClose();
          }}
          sx={{
            position: 'absolute',
            paddingBottom: 1,
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  );
};

export default function Login({ open, setOpen }: LoginPropsType) {
  const KAKAO_URL = process.env.NEXT_PUBLIC_KAKAO;
  const ClickOpen = () => {
    setOpen(true);
  };
  const ClickClose = () => {
    setOpen(false);
  };

  return (
    <div>
      <Dialog
        onClose={ClickClose}
        open={open}
        TransitionComponent={Transition}
        keepMounted
        PaperProps={{
          style: {
            backgroundImage: "url('/images/login.jpeg')",
            backgroundSize: 'cover',
            width: '30vw',
            height: '60vh',
          },
        }}
      >
        <CustomDialogTitle onClose={ClickClose} />
        <DialogContent>
          <LoginDiv>
            <div className="font-[ErrFont] text-7xl">Login</div>
            <ImageDiv
              onClick={() => {
                if (!KAKAO_URL) {
                  throw new Error('카카오 URL이 잘못되있습니다.');
                }
                location.href = KAKAO_URL;
              }}
            >
              <Image
                src="/images/kakao.png"
                alt="카카오 로그인"
                priority
                fill
                sizes="100%"
              />
            </ImageDiv>
          </LoginDiv>
        </DialogContent>
      </Dialog>
    </div>
  );
}
const LoginDiv = tw.div`
  flex
  flex-col
  justify-between
  items-center
  text-4xl
  w-full
  h-full
  overflow-hidden
  `;
const ImageDiv = tw.div`
  h-kakao-size
  w-kakao-size
  relative
`;
