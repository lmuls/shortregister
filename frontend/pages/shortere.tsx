import React from "react";
import { GetServerSideProps } from "next";
import CollapsibleTable from "../comps/Table";
import style from "../styles/Instrumenter.module.scss";
import { ShortPosition } from "./instrumenter";

export interface Shorter {
  id: number;
  companyName: string;
  shortPositions: ShortPosition[];
}

function Shortere({ shorters }: { shorters: Shorter[] }) {
  return (
    <div className={style.instruments}>
      <CollapsibleTable content={shorters} />
    </div>
  );
}

export default Shortere;

export const getServerSideProps: GetServerSideProps = async (context) => {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters";
  const res = await fetch(url);

  let shorters: Shorter[] = [];
  if (res.status == 200) {
    shorters = await res.json();
  } else {
    console.log("Error in request");
  }

  return { props: { shorters } };
};
