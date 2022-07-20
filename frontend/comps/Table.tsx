import * as React from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {Instrument} from "../pages/instrumenter";
import {ShortPosition} from "../pages/instrumenter";
import Link from "next/link";

function Row({ instrument }: {instrument: Instrument}) {
    const [open, setOpen] = React.useState(false);
    const active = instrument.shortPositions.filter(x => x.active).length > 0;
    const lastAction = instrument.shortPositions[0].history[0].date


    return (
        <React.Fragment>
            <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpen(!open)}
                    >
                        {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                    </IconButton>
                </TableCell>
                <TableCell component="th" scope="row">
                    <Link href={`/instrumenter/${instrument.isin}`}>
                        <a>
                            {instrument.issuerName}
                        </a>
                    </Link>
                </TableCell>
                <TableCell align="right">{active ? "Aktiv" : "Inaktiv"}</TableCell>
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
                                        <TableCell>Shorter</TableCell>
                                        <TableCell>Åpnet</TableCell>
                                        <TableCell>Lukket</TableCell>
                                        <TableCell align="right">Andel</TableCell>
                                        <TableCell align="right">Antall</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {instrument.shortPositions.slice(0, 2).map((historyRow) => (
                                        <TableRow key={historyRow.opened}>
                                            <TableCell component="th" scope="row">
                                                <Link href={`/shorter/${historyRow.companyName}`}>
                                                    <a>
                                                        {historyRow.companyName}
                                                    </a>
                                                </Link>
                                            </TableCell>
                                            <TableCell>{historyRow.opened}</TableCell>
                                            <TableCell>{historyRow.closed}</TableCell>
                                            <TableCell align="right">{historyRow.history[0].shortPercent}</TableCell>
                                            <TableCell align="right">{historyRow.history[0].shares}</TableCell>
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

export default function CollapsibleTable({instruments}: {instruments: Instrument[]}) {
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
                    {instruments.map((instrument) => (
                        <Row key={instrument.isin} instrument={instrument} />
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
