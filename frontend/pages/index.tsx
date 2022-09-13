import Image from "next/image";
import heroImg from "../public/115.jpg";
import styles from "../styles/Home.module.scss";

const Home = () => {
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
