import React from "react";
import { GetServerSideProps } from "next";
import CollapsibleTable from "../comps/Table";
import { ShortPositionDTO } from "./instrumenter";

export interface ShorterDTO {
  id: number;
  companyName: string;
  shortPositions: ShortPositionDTO[];
}

function Shortere({ shorters }: { shorters: ShorterDTO[] }) {
  shorters.sort((a, b) => {
    return a.companyName.localeCompare(b.companyName);
  });

  return (
    <div>
      <CollapsibleTable content={shorters} />
    </div>
  );
}

export default Shortere;

export const getServerSideProps: GetServerSideProps = async (context) => {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters";
  const res = await fetch(url);

  let shorters: ShorterDTO[] = [];
  if (res.status == 200) {
    shorters = await res.json();
  } else {
    console.log("Error in request");
  }

  return { props: { shorters } };
};
