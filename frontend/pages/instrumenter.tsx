import React from "react";
import { GetServerSideProps } from "next";
import CollapsibleTable from "../comps/Table";

export interface ShortPositionHistoryDTO {
  date: string;
  shares: number;
  shortPercent: number;
}

export interface ShortPositionDTO {
  issuerName: string;
  companyName: string;
  active: boolean;
  opened: string;
  closed: string;
  history: ShortPositionHistoryDTO[];
}

export interface InstrumentDTO {
  isin: string;
  issuerName: string;
  shortPositions: ShortPositionDTO[];
}

function Instrumenter({ instruments }: { instruments: InstrumentDTO[] }) {
  return (
    <div>
      <CollapsibleTable content={instruments} />
    </div>
  );
}

export default Instrumenter;

export const getServerSideProps: GetServerSideProps = async (context) => {
  const url = process.env.NEXT_PUBLIC_API_URL + "instruments";
  console.log(url);
  const res = await fetch(url);

  let instruments: InstrumentDTO[] = [];
  if (res.status == 200) {
    instruments = await res.json();
  } else {
    console.log("Error in request");
  }

  return { props: { instruments } };
};
