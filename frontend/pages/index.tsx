import type { GetServerSideProps } from "next";
import Image from "next/image";
import heroImg from "../public/115.jpg";
import styles from "../styles/Home.module.scss";
import { InstrumentDTO } from "./instrumenter";

const Home = (props: { instruments: InstrumentDTO[] }) => {
  return (
    <>
      <div id={styles.mainCont}>
        <Image
          src={heroImg}
          alt={"Cartonish image of a bull and a bear"}
          priority
        />
        <div className={styles.heroCont}>
          <h1>Welcome to the Short Register for OSEBX</h1>
          <p>Here you will find information of all stocks shorted above 2 %.</p>
        </div>
      </div>
    </>
  );
};

export default Home;

export const getServerSideProps: GetServerSideProps = async (context) => {
  const url = process.env.NEXT_PUBLIC_API_URL + "instruments";
  const res = await fetch(url);

  let instruments: InstrumentDTO[] = [];
  if (res.status == 200) {
    instruments = await res.json();
  } else {
    console.log("Error in request");
  }

  return { props: { instruments } };
};
