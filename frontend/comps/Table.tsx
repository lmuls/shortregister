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
import { Instrument } from "../pages/instrumenter";
import { ShortPosition } from "../pages/instrumenter";
import Link from "next/link";
import { Shorter } from "../pages/shortere";
import {formatDate, slugify} from "../util/mainUtils";

export interface TableElement {
  name: string;
  shortPositions: ShortPosition[];
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
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    {isInstrument ? (
                      <TableCell>Instrument</TableCell>
                    ) : (
                      <TableCell>Shorter</TableCell>
                    )}
                    <TableCell>Åpnet</TableCell>
                    <TableCell>Lukket</TableCell>
                    <TableCell align="right">Andel</TableCell>
                    <TableCell align="right">Antall</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {shortPositions.slice(0, 3).map((historyRow) => (
                    <TableRow key={historyRow.opened}>
                      <TableCell component="th" scope="row">
                        {!isInstrument ? (
                          <Link
                            href={`/instrumenter/${slugify(
                              historyRow.issuerName
                            )}`}
                          >
                            <a>{historyRow.issuerName}</a>
                          </Link>
                        ) : (
                          <Link
                            href={`/shortere/${slugify(
                              historyRow.companyName
                            )}`}
                          >
                            <a>{historyRow.companyName}</a>
                          </Link>
                        )}
                      </TableCell>
                      <TableCell>{formatDate(historyRow.opened)}</TableCell>
                      <TableCell>{formatDate(historyRow.closed)}</TableCell>
                      <TableCell align="right">
                        {historyRow.history[0].shortPercent}
                      </TableCell>
                      <TableCell align="right">
                        {historyRow.history[0].shares}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

function isInstrument(
  possibleInstrument: Instrument[] | Shorter[]
): possibleInstrument is Instrument[] {
  return (possibleInstrument as Instrument[])[0].issuerName !== undefined;
}

export default function CollapsibleTable({
  content,
}: {
  content: Shorter[] | Instrument[];
}) {
  if (isInstrument(content)) {
    return (
      <TableContainer component={Paper}>
        <Table stickyHeader aria-label="collapsible table">
          <TableHead>
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
