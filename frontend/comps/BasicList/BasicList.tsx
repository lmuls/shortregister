import * as React from 'react';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import InboxIcon from '@mui/icons-material/Inbox';
import DraftsIcon from '@mui/icons-material/Drafts';
import {types} from "util";
import {JSXElement} from "@babel/types";
import {Instrument} from "../../pages";


const BasicList = ({elements} : {elements: Instrument[] }): JSX.Element => {

    function mapElements(element: Instrument): JSX.Element {
        return (<ListItem disablePadding>
            <ListItemButton>
                <ListItemText primary={element.issuerName} />
            </ListItemButton>
        </ListItem>)
    }

    return (
        <Box sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
            <nav aria-label="main mailbox folders">
                <List>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemIcon>
                                <InboxIcon />
                            </ListItemIcon>
                            <ListItemText primary="Inbox" />
                        </ListItemButton>
                    </ListItem>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemIcon>
                                <DraftsIcon />
                            </ListItemIcon>
                            <ListItemText primary="Drafts" />
                        </ListItemButton>
                    </ListItem>
                </List>
            </nav>
            <Divider />
            <nav aria-label="secondary mailbox folders">

                <List>
                    <ListItem disablePadding>
                        <ListItemButton>
                            <ListItemText primary="Trash" />
                            <ListItemText primary="Trash" />
                        </ListItemButton>
                    </ListItem>
                    {elements.map((elem: Instrument) => {
                        return mapElements(elem);
                    })}
                </List>
            </nav>
        </Box>
    );
}

export default BasicList;