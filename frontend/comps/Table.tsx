import { withStyles } from "@mui/material";
import * as React from "react";
import Box from "@mui/material/Box";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import { InstrumentDTO } from "../pages/instrumenter";
import { ShortPositionDTO } from "../pages/instrumenter";
import Link from "next/link";
import { ShorterDTO } from "../pages/shortere";
import { formatDate, slugify } from "../util/mainUtils";
import { SubTable } from "./SubTable";

export interface TableElement {
  name: string;
  shortPositions: ShortPositionDTO[];
  isInstrument?: boolean;
}

function Row({ name, shortPositions, isInstrument }: TableElement) {
  const [open, setOpen] = React.useState(false);
  const active = shortPositions.filter((x) => x.active).length > 0;
  const lastAction = formatDate(shortPositions[0].history[0].date);

  return (
    <React.Fragment>
      <TableRow sx={{ "& > *": { borderBottom: "unset" } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell size={"medium"} component="th" scope="row">
          {isInstrument ? (
            <Link href={`/instrumenter/${slugify(name)}`}>
              <a className={"instrument-name"}>{name}</a>
            </Link>
          ) : (
            <Link href={`/shortere/${slugify(name)}`}>
              <a className={"instrument-name"}>{name}</a>
            </Link>
          )}
        </TableCell>
        <TableCell className={active ? "active" : "inactive"} align="right">
          {active ? "Aktiv" : "Inaktiv"}
        </TableCell>
        <TableCell align="right">{lastAction}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Siste posisjoner
              </Typography>
              <SubTable
                shortPositions={shortPositions.slice(0, 3)}
                isInstrument={isInstrument ?? true}
              />
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

function isInstrument(
  possibleInstrument: InstrumentDTO[] | ShorterDTO[]
): possibleInstrument is InstrumentDTO[] {
  return (possibleInstrument as InstrumentDTO[])[0].issuerName !== undefined;
}

export default function CollapsibleTable({
  content,
}: {
  content: ShorterDTO[] | InstrumentDTO[];
}) {
  if (isInstrument(content)) {
    return (
      <TableContainer component={Paper}>
        <Table stickyHeader aria-label="collapsible table">
          <TableHead style={{ backgroundColor: "red", color: "red" }}>
            <TableRow>
              <TableCell />
              <TableCell>Instrument</TableCell>
              <TableCell align="right">Aktiv(e) posisjoner</TableCell>
              <TableCell align="right">Siste endring</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {content.map((element) => (
              <Row
                name={element.issuerName}
                shortPositions={element.shortPositions}
                isInstrument={true}
                key={element.isin}
              />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  } else {
    return (
      <TableContainer component={Paper}>
        <Table stickyHeader aria-label="collapsible table">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell>Shorter</TableCell>
              <TableCell align="right">Aktiv(e) posisjoner</TableCell>
              <TableCell align="right">Siste endring</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {content.map((element) => (
              <Row
                name={element.companyName}
                shortPositions={element.shortPositions}
                key={element.id}
              />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    );
  }
}
