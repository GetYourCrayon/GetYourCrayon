import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Image from 'next/image';
import { styled } from '@mui/material';
import { useEffect, useState } from 'react';

interface PropsTypes {
  gachaIdx: number;
  gachaImg: string;
}

const CardContentNoPadding = styled(CardContent)(`
  padding: 0;
  &:last-child {
    padding-bottom: 0;
  }
`);

export default function CollectionCard({
  data,
  key,
}: {
  data: PropsTypes;
  key: number;
}) {
  return (
    <Card className="flex flex-col items-center relative" sx={{ margin: 1 }}>
      <CardContentNoPadding>
        <div className="w-bomul-img h-bomul-img relative ">
          <Image src={data.gachaImg} alt="no_img" priority fill sizes="100%" />
        </div>
      </CardContentNoPadding>
    </Card>
  );
}
