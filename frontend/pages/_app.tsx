import "../styles/globals.css";
import type { AppProps } from "next/app";
import NavArea from "../comps/NavArea";
import { ThemeProvider, createTheme } from "@mui/material/styles";

const darkTheme = createTheme({
  palette: {
    mode: "dark",
  },
});

function MyApp({ Component, pageProps }: AppProps) {
  return (
    <ThemeProvider theme={darkTheme}>
      <NavArea>
        <Component {...pageProps} />
      </NavArea>
    </ThemeProvider>
  );
}

export default MyApp;
