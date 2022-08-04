import * as React from "react";
import styles from "./NavAreaStyle.module.scss";
import { ReactNode } from "react";
import Image from "next/image";
import Link from "next/link";

const pages = ["Instrumenter", "Shortere", "Nyeste posisjoner"];
const settings = ["Profile", "Account", "Dashboard", "Logout"];

export default function NavArea({ children }: { children: ReactNode }) {
  const renderLinks = (links: string[]) => {
    return links.map((link) => {
      return (
        <Link key={link} href={"/" + link.toLowerCase()}>
          <a className={styles.link}>{link}</a>
        </Link>
      );
    });
  };

  return (
    <div id={styles.mainFrame}>
      <div id={styles.navArea}>
        <nav className={styles.topNav}>
          <div className={styles.topNavLeft}>
            <Link href={"/"}>
              <a>
                <Image
                  src={"/teddy-bear_1f9f8.png"}
                  width={"100px"}
                  height={"100px"}
                  alt={"Teddybear"}
                />
              </a>
            </Link>
            <div className={styles.links}>{renderLinks(pages)}</div>
          </div>
          <div className={styles.topNavRight}></div>
        </nav>
        <div id={styles.pageContents}>{children}</div>
      </div>
    </div>
  );
}
