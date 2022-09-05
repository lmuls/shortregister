import { Chip } from "@mui/material";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Link from "next/link";
import * as React from "react";
import { ShortPositionDTO } from "../pages/instrumenter";
import { formatDate, slugify } from "../util/mainUtils";

export function SubTable({
  shortPositions,
  isInstrument,
}: {
  shortPositions: ShortPositionDTO[];
  isInstrument: boolean;
}) {
  return (
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
          <TableCell>Oppdatert</TableCell>
          <TableCell align="right">Andel</TableCell>
          <TableCell align="right">Antall</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {shortPositions.map((historyRow) => (
          <TableRow key={historyRow.opened}>
            <TableCell component="th" scope="row">
              {!isInstrument ? (
                <Link href={`/instrumenter/${slugify(historyRow.issuerName)}`}>
                  <a>{historyRow.issuerName}</a>
                </Link>
              ) : (
                <Link href={`/shortere/${slugify(historyRow.companyName)}`}>
                  <a>{historyRow.companyName}</a>
                </Link>
              )}
            </TableCell>
            <TableCell>{formatDate(historyRow.opened)}</TableCell>
            <TableCell>
              {historyRow.closed ? (
                formatDate(historyRow.closed)
              ) : (
                <Chip
                  label={"Active position"}
                  variant="outlined"
                  color="success"
                />
              )}
            </TableCell>
            <TableCell>
              {!historyRow.closed && formatDate(historyRow.history[0].date)}
            </TableCell>
            <TableCell align="right">
              {historyRow.history[0].shortPercent}
            </TableCell>
            <TableCell align="right">{historyRow.history[0].shares}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
