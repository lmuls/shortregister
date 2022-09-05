import React from "react";
import { DetailDashboard } from "../../comps/DetailDashboard";
import { slugify } from "../../util/mainUtils";
import { ShorterDTO } from "../shortere";

export function Shorter({ shorterInfo }: { shorterInfo: ShorterDTO }) {
  return (
    <DetailDashboard
      id={shorterInfo.id.toString()}
      name={shorterInfo.companyName}
      shortPositions={shorterInfo.shortPositions}
    />
  );
}

export async function getStaticPaths() {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters";
  const res = await fetch(url);

  const json = await res.json();

  const paths = json.map((shorter: ShorterDTO) => {
    return { params: { Shorter: slugify(shorter.companyName) } };
  });

  return { paths: paths, fallback: false };
}

export async function getStaticProps({
  params,
}: {
  params: { Shorter: string };
}) {
  const url = process.env.NEXT_PUBLIC_API_URL + "shorters/" + params.Shorter;
  const res = await fetch(url);
  const shorterInfo = await res.json();

  return { props: { shorterInfo } };
}

export default Shorter;
