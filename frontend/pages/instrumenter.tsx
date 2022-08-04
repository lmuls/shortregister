import React from "react";
import { GetServerSideProps } from "next";
import CollapsibleTable from "../comps/Table";
import style from "../styles/Instrumenter.module.scss";

export interface ShortPositionHistoryDto {
  date: string;
  shares: number;
  shortPercent: number;
}

export interface ShortPosition {
  issuerName: string;
  companyName: string;
  active: boolean;
  opened: string;
  closed: string;
  history: ShortPositionHistoryDto[];
}

export interface Instrument {
  isin: string;
  issuerName: string;
  shortPositions: ShortPosition[];
}

function Instrumenter({ instruments }: { instruments: Instrument[] }) {
  return (
    <div className={style.instruments}>
      <CollapsibleTable content={instruments} />
    </div>
  );
}

export default Instrumenter;

export const getServerSideProps: GetServerSideProps = async (context) => {
  const url = process.env.API_URL + "api/instruments";
  const res = await fetch(url);

  let instruments: Instrument[] = [];
  if (res.status == 200) {
    instruments = await res.json();
  } else {
    console.log("Error in request");
  }

  return { props: { instruments } };
};
